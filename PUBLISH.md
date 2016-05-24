## Publish wiki

Retrieve latest changes:

```
git checkout gh-pages
git pull
git checkout master
```

Generate and publish:

```
rake generate
rake publish
```
