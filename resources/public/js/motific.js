require({
  paths: {
    jquery: [
      '//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min',
      'lib/jquery-min'
    ],
    underscore: 'lib/underscore-min',
    backbone: 'lib/backbone-min',

    // rjs plugins
    tpl: 'lib/tpl',
    domReady: 'lib/domReady'
  }
},
['views/BodyView', 'domReady!'],
function(BodyView) {

  var bv = new BodyView({el: $('body')});

});
