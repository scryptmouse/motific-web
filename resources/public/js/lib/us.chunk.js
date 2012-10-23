define(['underscore'], function(_) {
  function chunkArray(arr, chunk) {
    var i, j, tmp = [];

    for (i = 0, j = arr.length; i < j; i += chunk) {
      tmp.push(arr.slice(i, i + chunk));
    }

    return tmp;
  }

  _.mixin({chunk: chunkArray});

  return _;
});
