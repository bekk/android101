//
//  SpotifyPlayerViewController.h
//  SongFinder
//
//  Created by Hans M. Inderberg on 9/24/13.
//  Copyright (c) 2013 HM&M. All rights reserved.
//

#import <UIKit/UIKit.h>
@class SpotifyTrack;

@interface SpotifyPlayerViewController : UIViewController
@property (nonatomic, assign) SpotifyTrack *track;
@end
