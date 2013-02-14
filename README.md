Java Service to Update Music Solr Search Schema

Two feeds (JSON/XML), one Solr schema.
One feed an artist XML database from the public site, Musicbrainz.
The other feed JSON of all artist audio/video clips in the BBC.
The service merges these feeds and populates a Solr instance.
The service is wrapped in a Quartz scheduler to run hourly.
Lots of clever things happen with weighting and priority.

The end result is an in production auto complete search box that can be viewed [here](http://www.bbc.co.uk/music/showcase)

Great project to be involved in, we were the first team to get a Solr instance into production at the BBC.

