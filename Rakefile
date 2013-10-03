require 'jekyll'

desc "Generate site"
task :generate do
  Jekyll::Site.new(Jekyll.configuration({
    "source"      => "docs",
    "destination" => "_docs"
  })).process
end
