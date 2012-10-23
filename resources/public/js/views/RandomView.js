define(
[
  'jquery', 'underscore', 'backbone',
  'views/_PaneView',
  'lib/us.chunk'
], function($, _, Backbone, PaneView) {

  var RandomView = PaneView.extend({
    exactBlur: function(ev) {
      this.$('input[name="min"], input[name="max"]').val('');
    },

    minMaxBlur: function(ev) {
      this.$('input[name="exact"]').val('');
    }
  });

  // Override default rendering for lists
  RandomView.prototype._api_render_list = function(words) {
    var chunks = Math.ceil(words.length / 4)
      , chunked = _.chunk(words.sort(), chunks)
      , $ul = $('#list ul');

    $ul.empty();

    _(chunked).each(function(chunk) {
      var $container_li = $('<li></li>').addClass('four columns')
        , $sub_ul = $('<ul></ul>');

      _(chunk).each(function(word) {
        var $li = $('<li></li>').text(word);
        $sub_ul.append($li);
      });

      $container_li.append($sub_ul);
      $ul.append($container_li);
    });
  };

  RandomView.prototype.events = _({}).extend(
    RandomView.prototype.events, {
    'change input[name="min"]': 'minMaxBlur',
    'change input[name="max"]': 'minMaxBlur',
    'change input[name="exact"]': 'exactBlur'
  });

  return RandomView;
});
