define(
[
  'jquery','underscore','backbone',
  'views/RandomView','views/PermutateView','views/AnagramView'],
function($, _, Backbone, RandomView, PermutateView, AnagramView) {
  var BodyView = Backbone.View.extend({
    events: {
      'click .panes-nav li a': 'switchPane',
      'click #response pre': 'selectAll'
    },

    initialize: function() {
      var rv, pv, av;

      rv = new RandomView({el: $('#random')});
      pv = new PermutateView({el: $('#permutate')});
      av = new AnagramView({el: $('#anagram')});
    },

    selectAll: function(ev) {
      var doc = document
        , el = ev.currentTarget
        , range, selection
      ;

      if (doc.body.createTextRange) {
        range = doc.body.createTextRange();
        range.moveToElementText(el);
        range.select();
      } else if (window.getSelection) {
        selection = window.getSelection();
        range = doc.createRange();
        range.selectNodeContents(el);
        selection.removeAllRanges();
        selection.addRange(range);
      }

      ev.preventDefault();
      return false;
    },

    switchPane: function(ev) {
      var $anchor = $(ev.currentTarget)
        , $pane = $('#' + $anchor.data('target'));

      if ($pane.length > 0 && $pane.is(':not(.active)')) {
        $pane.addClass('active').siblings().removeClass('active');
        $anchor.parent('li').addClass('active').siblings().removeClass('active');
      }

      ev.preventDefault();
      return false;
    }
  });

  return BodyView;
});
