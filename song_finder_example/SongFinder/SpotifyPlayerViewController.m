//
//  SpotifyPlayerViewController.m
//  SongFinder
//
//  Created by Hans M. Inderberg on 9/24/13.
//  Copyright (c) 2013 HM&M. All rights reserved.
//

#import "SpotifyPlayerViewController.h"
#import "SpotifyTrack.h"
#import "SpotifyService.h"

@interface SpotifyPlayerViewController ()
@property (weak, nonatomic) IBOutlet UIImageView *imageView;
@end

@implementation SpotifyPlayerViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [SpotifyService getDetailsForTrack:self.track result:^(SpotifyTrack *track) {
        
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
            
            UIImage *image = [UIImage imageWithData:[NSData dataWithContentsOfURL:track.thumbnailUrl]];
            
            dispatch_async(dispatch_get_main_queue(), ^{
                self.imageView.image = image;
            });
        });
        
        
    } error:^(NSError *error) {
        
    }];
   

}

- (IBAction)openInSpotifyButtonTouch:(id)sender {
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:[NSString stringWithFormat:@"spotify://%@", self.track.id]]];
}
@end
