// TreeGrid Node
module qpp014.a.viewmodel {
    export class ScreenModel {
        //
        
        viewmodelb = new qpp014.b.viewmodel.ScreenModel();
        viewmodeld = new qpp014.d.viewmodel.ScreenModel();
        viewmodelg = new qpp014.g.viewmodel.ScreenModel();
        viewmodelh = new qpp014.h.viewmodel.ScreenModel();

        //viewmodel A
        a_SEL_001_items: KnockoutObservableArray<shr.viewmodelbase.PayDayProcessing>;
        a_SEL_001_itemSelected: KnockoutObservable<any>;

        constructor() {
            
            $('.func-btn').css('visibility', 'hidden');
            $('#screenB').css('display', 'none');
            
            let self = this;
            

            //viewmodel A
            self.a_SEL_001_items = ko.observableArray([
                new shr.viewmodelbase.PayDayProcessing('1', 1, '1', 1, 1, 1, 1),
                new shr.viewmodelbase.PayDayProcessing('2', 2, '2', 2, 2, 2, 2),
                new shr.viewmodelbase.PayDayProcessing('3', 3, '3', 3, 3, 3, 3)
            ]);
            self.a_SEL_001_itemSelected = ko.observable(1);

        }
        nextScreen() {
            $("#screenA").css("display", "none");
            $("#screenB").css("display", "");
            $("#screenB").ready(function() {
                $(".func-btn").css("visibility", "visible");
            });
        }
        backScreen() {
            $("#screenB").css("display", "none");
            $("#screenA").css("display", "");
            $(".func-btn").css("visibility", "hidden");
        }
        
    }
};
