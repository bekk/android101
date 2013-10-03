//
//  SpotifyService.h
//  SongFinder
//
//  Created by Hans M. Inderberg on 9/24/13.
//  Copyright (c) 2013 HM&M. All rights reserved.
//

#import <Foundation/Foundation.h>
@class SpotifyTrack;

typedef void(^KSpotifyTrackSearchResult)(NSArray* tracks);
typedef void(^KSpotifyTrackResult)(SpotifyTrack* track);
typedef void(^KSpotifyServiceError)(NSError* error);

@interface SpotifyService : NSObject
+(void)findTracks:(NSString *)title result:(KSpotifyTrackSearchResult)result error:(KSpotifyServiceError)error;
+ (void)getDetailsForTrack:(SpotifyTrack *)track result:(KSpotifyTrackResult)result error:(KSpotifyServiceError)error;
@end
