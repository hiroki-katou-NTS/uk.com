module nts.uk.com.view.cps009.b {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ViewModel();
        __viewContext["viewModel"].start().done(function(data) {
            __viewContext.bind(__viewContext["viewModel"]);
            $('#grid_B_virtualContainer').attr("tabindex", "1");
            $("#grid_B_headers>thead>tr>th:nth-child(1)>span").focus();
            $('span[data-role*="checkbox"]').attr("tabindex", "2");
            let beforeIndex = -1;
            $(window).keyup((e)=>{
                 if(e.which === 9){
                    
                    let tabindex = e.target.attributes.tabindex ?e.target.attributes.getNamedItem("tabindex").value:e.target.attributes.getNamedItem("tab-index").value;
                    if(beforeIndex == 6){
                         $("#grid_B_headers>thead>tr>th:nth-child(1)>span").focus();
                    }
                    beforeIndex = parseInt(tabindex);
                    
                }
              
            });
        });

    });
}

$(document).delegate("#grid_B", "iggridrowsrendered", function(evt, ui) {
    if ($("#grid_B").data("igGrid") === undefined) {
        return;
    }

    var ds = ui.owner.dataSource.data();
    $(ds)
        .each(
        function(index, el: any) {
            let nameCell = $("#grid_B").igGrid("cellAt", 0, index);
            let referenceCell = $("#grid_B").igGrid("cellAt", 1, index);
            if (el.isRequired == 1) {
                $(nameCell).addClass('requiredCell');
                $(referenceCell).addClass('requiredCell');
            } else {
                $(nameCell).addClass('notrequiredCell');
                $(referenceCell).addClass('notrequiredCell');
            }

        });
});





