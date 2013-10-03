//
//  SpotifyService.m
//  SongFinder
//
//  Created by Hans M. Inderberg on 9/24/13.
//  Copyright (c) 2013 HM&M. All rights reserved.
//

#import "SpotifyService.h"
#import "KURLConnection.h"
#import "SpotifyTrack.h"

@implementation SpotifyService

+ (void)findTracks:(NSString *)title result:(KSpotifyTrackSearchResult)result error:(KSpotifyServiceError)error {
    
    NSString *urlString = [NSString stringWithFormat:@"http://ws.spotify.com/search/1/track.json?q=%@", [title stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]];
    
    [KURLConnection startWithRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:urlString]] successHandler:^(NSData *response) {
        NSError *convertError = nil;
        NSArray *tracks = [SpotifyService convertDataResponseToTracks:response error:&convertError];
        
        if (convertError != nil) {

            error(convertError);
        }
        
        result(tracks);
    } failedHandler:^(NSError *requestError) {
        error(requestError);
    }];
}

+ (void)getDetailsForTrack:(SpotifyTrack *)track result:(KSpotifyTrackResult)result error:(KSpotifyServiceError)error {
    NSString *urlString = [NSString stringWithFormat:@"https://embed.spotify.com/oembed/?url=%@", track.id];
    
    [KURLConnection startWithRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:urlString]] successHandler:^(NSData *response) {
        NSError *convertError = nil;
        [SpotifyService decorateTrack:track withDetails:response error:&convertError];
        
        if (convertError != nil) {
            error(convertError);
        }
        
        result(track);
    } failedHandler:^(NSError *requestError) {
        error(requestError);
    }];
    
}

+ (void)decorateTrack:(SpotifyTrack *)track withDetails:(NSData *)details error:(NSError **)error {
    NSError __autoreleasing *jsonError = nil;
    NSDictionary *dictionary = [NSJSONSerialization JSONObjectWithData:details options:NSJSONReadingAllowFragments error:&jsonError];
    
    if (jsonError != nil) {
        NSLog(@"Failed parsing json: %@", [jsonError localizedDescription]);
        error = &jsonError;
    }
    
    track.thumbnailUrl = [NSURL URLWithString:dictionary[@"thumbnail_url"]];
}

+ (NSArray *)convertDataResponseToTracks:(NSData *)response error:(NSError **)error {
    
    NSError __autoreleasing *jsonError = nil;
    NSDictionary *dictionary = [NSJSONSerialization JSONObjectWithData:response options:NSJSONReadingAllowFragments error:&jsonError];
    
    if (jsonError != nil) {
        NSLog(@"Failed parsing json: %@", [jsonError localizedDescription]);
        error = &jsonError;
    }
    
    NSMutableArray *tracks = [[NSMutableArray alloc] init];
    for (NSDictionary *track in dictionary[@"tracks"]) {
        [tracks addObject:[SpotifyService convertResponseTrackToTrack:track]];
    }
    
    return tracks;
}

+ (SpotifyTrack *)convertResponseTrackToTrack:(NSDictionary *)trackObject {
    SpotifyTrack *track = [[SpotifyTrack alloc] init];
    
    track.id = trackObject[@"href"];
    track.title = trackObject[@"name"];
    track.artist = trackObject[@"artists"][0][@"name"];
    
    return track;
}

@end
