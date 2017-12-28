module nts.uk.com.view.cps009.a {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ViewModel();
        __viewContext["viewModel"] = screenModel;
        __viewContext.bind(__viewContext["viewModel"]);
        $(".ntsSearchBox").attr("placeholder", "名称で検索…");
        $(".search-btn").html("絞り込み");
        $(document).delegate("#combo-box", "igcomboselectionchanging", function(evt, ui) {
            debugger;
            //use to obtain reference to the event browser
            evt.originalEvent;
            //use to obtain reference to igCombo
            ui.owner;
            //use to obtain reference to array of new selected items. That can be null.
            ui.items;
            //use to obrain reference to array of currently selected items.
            ui.currentItems
        });
        
        
        $(document).delegate("#combo-box", "igcomboselectionchanged", function(evt, ui) {
            //use to obtain reference to the event browser
            evt.originalEvent;
            //use to obtain reference to igCombo
            ui.owner;
            //use to obtain reference to array of new selected items. That can be null.
            ui.items;
            //use to obtain reference to array of old selected items. That can be null.
            ui.oldItems;
        });

    });
}