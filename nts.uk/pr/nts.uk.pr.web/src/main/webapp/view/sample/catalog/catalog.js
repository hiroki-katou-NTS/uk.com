$(function(){

	$(document).tooltip();
	$("#side-menu").load("/nts.uk.pr.web/view/sample/catalog/sidemenu.xhtml", function(){
	    $("#side-menu a").each(function () {
	        var href = $(this).attr('href');
	        if (window.location.pathname === href) {
	            $(this).addClass('active');
	        }
	    });
	});
	
	$('button').click(function(){
		window.location = $(this).data("href");
	});
	
	$('pre').each(function(){
		$(this).html($(this).html().replace("<!--[CDATA[","").replace("]]-->",""));
	})
	
});