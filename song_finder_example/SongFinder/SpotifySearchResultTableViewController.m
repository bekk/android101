//
//  SpotifySearchResultTableViewController.m
//  SongFinder
//
//  Created by Hans M. Inderberg on 9/24/13.
//  Copyright (c) 2013 HM&M. All rights reserved.
//

#import "SpotifySearchResultTableViewController.h"
#import "SpotifyService.h"
#import "SpotifyTrack.h"
#import "SpotifyPlayerViewController.h"

@interface SpotifySearchResultTableViewController ()
@property (nonatomic, strong) NSArray* tracks;
@end

@implementation SpotifySearchResultTableViewController

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.tracks.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"trackCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    SpotifyTrack *track = self.tracks[indexPath.row];
    cell.textLabel.text = track.title;
    cell.detailTextLabel.text = track.artist;
    
    return cell;
}

#pragma mark - Navigation

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    SpotifyPlayerViewController *viewController = (SpotifyPlayerViewController *)[segue destinationViewController];
    viewController.track = self.tracks[[[self.tableView indexPathForSelectedRow] row]];
}

#pragma mark - UISearchBarDelegate

- (void) searchBarSearchButtonClicked:(UISearchBar *)searchBar {
    [searchBar resignFirstResponder];
    
    [SpotifyService findTracks:searchBar.text result:^(NSArray *tracks) {
        self.tracks = tracks;
        [self.tableView reloadData];
    } error:^(NSError *data) {
        UIAlertView * alertView = [[UIAlertView alloc] initWithTitle:@"Feilet" message:@"Feilet skikkelig" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
        [alertView show];
    }];
}

@end
