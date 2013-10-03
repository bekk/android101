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
	sleep 1
	branch = `git rev-parse --abbrev-ref HEAD`
	branch.strip!
	if branch != "gh-pages" then
		abort "on #{branch} - not on gh-pages. Aborting."
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