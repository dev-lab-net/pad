var createPad = function() {
	$('#link').html('')
	$.post({
		url: 'http://localhost:8080/api/v1/pad',
		crossDomain: true,
		contentType: 'application/json',
		data: JSON.stringify({
			author: $('#author').val() || 'Anonymous',
			title: $('#title').val(),
			content: $('#content').val(),
		}),
	}).done(function(pad) {
		console.log(pad);
		$('#link').html(`<a href="http://localhost:8080/api/v1/pad/${pad.partialHash}">http://localhost:8080/api/v1/pad/${pad.partialHash}</a>`);
	});
};
