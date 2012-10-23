/*
 * @author Tobias Cohen
 * @source http://stackoverflow.com/questions/1184624/convert-form-data-to-js-object-with-jquery/1186309#1186309
 */

define(['jquery'], function($) {
  $.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
  };

  return $;
});
