//
//  SpotifyTrack.h
//  SongFinder
//
//  Created by Hans M. Inderberg on 9/24/13.
//  Copyright (c) 2013 HM&M. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SpotifyTrack : NSObject
@property (nonatomic, strong) NSString *id;
@property (nonatomic, strong) NSString *artist;
@property (nonatomic, strong) NSString *title;
@property (nonatomic, strong) NSURL *thumbnailUrl;
@end
