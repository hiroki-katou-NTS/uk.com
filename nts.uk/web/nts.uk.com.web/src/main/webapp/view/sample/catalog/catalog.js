$(function(){
	
	$("#side-menu").load("/nts.uk.com.web/view/sample/catalog/sidemenu.xhtml", function(){
	    $("#side-menu a").each(function () {
	        var href = $(this).attr('href');
	        if (window.location.pathname === href) {
	            $(this).addClass('active flash');
	        }
	    });
	    //collapse menu	    
	    var menu = $("#side-menu").find("h1");
	    for (var i = 0; i < menu.length; i++) {
	        menu[i].onclick = function(e) {
	        	//if($(e.target).next().css("display")!="none") return;
	        	// for (var i = 0; i < menu.length; i++) {
		        //	if($(menu[i]).next().find(".active").length==0&&$(menu[i]).next().css("display")!="none"){
		 	    //    	$(menu[i]).next().slideToggle();
		 	     //   }
	        	// }
	            $(e.target).next().slideToggle();
	            
	        }
	        collapseAllMenu();
	        
	    }
	    //collapse all except activating menu
	    function collapseAllMenu(){
	    	for (var i = 0; i < menu.length; i++) {
	    		if($(menu[i]).next().find(".active").length==0){
		        	$(menu[i]).next().css("display","none");	
		        }
	    	}
	    }
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
	    
	    // Search
	    $('#search-menu').keyup(function(){
	    	var searchbox = $(this);
	    	var searchtext = searchbox.val().toLowerCase();
	    	if(searchtext.length==0){
	    		collapseAllMenu();
	    		return;
	    	}
    		$("#side-menu li").show();
	    	if (searchbox.val().length > 0)
		    	$("#side-menu li").each(function(){
		    		if($(this).text().toLowerCase().indexOf(searchtext) === -1) {
		    			$(this).hide();
		    		}else{
		    			//if expand menu that has match item
		    			if($(this).parent("ul").css("display")=="none"){
		    				$(this).parent("ul").slideToggle();
		    			}
		    		}
		    	})
	    })
	});
	
	$('button.next, button.previous').click(function(){
		window.location = $(this).data("href");
	});
	
	$('pre').each(function(){
		$(this).html($(this).html().replace("<!--[CDATA[","").replace("]]-->",""));
	});
	
	$('pre').addClass('prettyprint')
	$.getScript("https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js");
	
	$(".tabs").tabs();
	
	$(".catalog-accordion").accordion({
		active: false,
		animate: false,
		collapsible: true
	});
	
});