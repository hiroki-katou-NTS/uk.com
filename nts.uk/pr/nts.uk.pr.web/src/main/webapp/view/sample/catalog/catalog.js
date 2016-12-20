$(function(){
	
	$("#side-menu").load("/nts.uk.pr.web/view/sample/catalog/sidemenu.xhtml", function(){
	    $("#side-menu a").each(function () {
	        var href = $(this).attr('href');
	        if (window.location.pathname === href) {
	            $(this).addClass('active flash');
	        }
	    });
	    
	    $("#content").css("min-height", $("#side-menu").outerHeight());
	    $("#side-menu").css("min-height", $("#content").outerHeight());
	    
	    // Auto bind prev/next button
	    $(".previous").each(function(){
			if($(this).data("href") !== null) {
				var href = "#";
				if($("#side-menu a.active").closest("li").prev().length !== 0)
					href = $("#side-menu a.active").closest("li").prev().find('a').attr("href");
				else if((index = $("#side-menu a.active").closest("ul").index("ul")) >= 1)
					href = $("#side-menu").find("ul").eq(index-1).find('li:last-child a').attr("href");
	    		$(this).data("href", href)
			}
	    });
	    $(".next").each(function(){
			if($(this).data("href") !== null) {
				var href = "#";
				if($("#side-menu a.active").closest("li").next().length !== 0)
					href = $("#side-menu a.active").closest("li").next().find('a').attr("href");
				else if((index = $("#side-menu a.active").closest("ul").index("ul")) < $("#side-menu").find("ul").length)
					href = $("#side-menu").find("ul").eq(index+1).find('li:first-child a').attr("href");
	    		$(this).data("href", href)
			}
	    });
	});
	
	$('button.next, button.previous').click(function(){
		window.location = $(this).data("href");
	});
	
	$('pre').each(function(){
		$(this).html($(this).html().replace("<!--[CDATA[","").replace("]]-->",""));
	});
	
	$(".tabs").tabs();
});