define(['jquery', 'underscore', 'lib/jquery.serializeObject', 'lib/us.chunk'], function($, _) {

  function API(options) {
    options = options || {};

    if (!_.isObject(this.prototype.events))
      this.prototype.events = {};

    this.prototype.events['submit form'] = '_api_form_submit';

    this.prototype._api_render_list = function(words) {
      var byLengths = _(words.sort()).groupBy('length')
        , $ul = $('#list ul:first');

      $ul.empty();

      // Skip one-letter permutations
      delete byLengths['1'];

      _(byLengths).each(function(lengthGroup, len) {
        var $row = $('<div></div>').addClass('row')
          , $container_li = $('<li></li>')
          , chunked = _.chunk(lengthGroup, Math.ceil(lengthGroup.length / 4));

        var $header = $('<h5></h5>').text('Length: ' + len).addClass('clearfix');

        $container_li.append($header);

        _(chunked).each(function(chunk) {
          var $chunk_ul = $('<ul></ul>').addClass('four columns');

          _(chunk).each(function (word) {
            var $li = $('<li></li>').text(word);
            $chunk_ul.append($li);
          });

          $row.append($chunk_ul);
        });

        $container_li.append($row);
        $ul.append($container_li);
      });
    };

    this.prototype._api_render_raw = function(words) {
      $('#raw pre').empty().text(words.join('\n'));
    };

    this.prototype._api_render_errors = function(errors) {
      alert(errors.join('\n'));
    };

    this.prototype._api_process = function(resp) {
      var words = resp.words || []
        , errors = resp.errors || []
        , filtered;

      $('data.total').text(words.length);

      if (errors.length === 0) {
        this._api_render_raw(words);
        this._api_render_list(words);
      } else {
        this._api_render_errors(errors);
      }
    };

    this.prototype._api_form_submit = function(ev) {
      var $form = $(ev.currentTarget)
        , data = $form.serializeObject();

      ev.preventDefault();
      $.getJSON($form.prop('action'), data, _(this._api_process).bind(this));

      return false;
    };
  }

  return API;
});
