define(['jquery', 'underscore', 'backbone', 'mixins/api'], function($, _, Backbone, API) {

  var PaneView = Backbone.View.extend({});

  API.call(PaneView);

  return PaneView;
});
