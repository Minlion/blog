


$(document).ready(function (){

    var l = $( '.ladda-button-demo' ).ladda();
	
	$("#login").submit(function(){
		l.ladda( 'start' );
		$.ajax({
			url:$(this).attr('action'),
			data:$('#login').serialize(),
			type:$(this).attr('method'),
			error:function(request){
				l.ladda('stop');
			},
			success:function(data){
				l.ladda('stop');
				if(data.success == false){
					
				}else{
					
				}
				console.log(data);
			}
		});
		//超时未响应
		setTimeout(function(){
	              l.ladda('stop');
	          },4000);
		return false;
	});
	

});

$(function() {
	

    function containerHeight() {
        var availableHeight = $(window).height() - $('body > .navbar').outerHeight() - $('body > .navbar + .navbar').outerHeight() - $('body > .navbar + .navbar-collapse').outerHeight();

        $('.page-container').attr('style', 'min-height:' + availableHeight + 'px');
    }

    $(window).on('resize', function() {
        setTimeout(function() {
            containerHeight();
        }, 100);
    }).resize();

    
});
