require 'jekyll'
require 'tmpdir'

desc "Generate site"
task :generate do
  Jekyll::Site.new(Jekyll.configuration({
	"source"      => "docs",
	"destination" => "_docs"
  })).process
end

desc "Generate and publish to gh-pages"
task :publish => [:generate] do
  Dir.mktmpdir do |tmp|
	system "mv _docs/* #{tmp}"
	system "git checkout gh-pages"

	branch = `git rev-parse --abbrev-ref HEAD`.strip
	required_branch = "gh-pages"
	destination = `git config --get remote.origin.url`.strip
	required_destination = "git@github.com:hinderberg/ios101.git"

	unless branch == required_branch && destination == required_destination then
		abort "Must be on #{required_branch} with #{required_destination} as the remote/origin. Aborting."
	end
	
	system "rm -rf *"
	system "mv #{tmp}/* ."
	message = "Site updated #{Time.now.utc}"
	system "git add ."
	system "git commit -am #{message.shellescape}"
	system "git push origin gh-pages --force"
	system "git checkout master"
	system "Published"
  end
end