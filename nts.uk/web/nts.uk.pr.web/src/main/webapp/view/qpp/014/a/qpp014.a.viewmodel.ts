// TreeGrid Node
module qpp014.a.viewmodel {
    export class ScreenModel {
        viewmodelb = new qpp014.b.viewmodel.ScreenModel();
        viewmodeld = new qpp014.d.viewmodel.ScreenModel();
        viewmodelg = new qpp014.g.viewmodel.ScreenModel();
        viewmodelh = new qpp014.h.viewmodel.ScreenModel();

        //viewmodel A
        a_SEL_001_items: KnockoutObservableArray<shr.viewmodelbase.PayDayProcessing>;
        a_SEL_001_itemSelected: KnockoutObservable<any>;

        constructor() {
            var self = this;
            $('.func-btn').css('visibility', 'hidden');
            $('#screenB').css('display', 'none');

            //viewmodel A
            self.a_SEL_001_items = ko.observableArray([]);
            self.a_SEL_001_itemSelected = ko.observable(1);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.findAll().done(function() {
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }
        
        /**
         * get data from table PAYDAY_PROCESSING
         */
        findAll(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            //get data with BONUS_ATR(PAY_BONUS_ATR) = 0 
            qpp014.a.service.findAll(0)
                .done(function(data) {
                    if (data.length > 0) {
                        self.a_SEL_001_items(data);
                    } else {
                        nts.uk.ui.dialog.alert("対象データがありません。");//ER010
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    dfd.reject(res);
                });
            return dfd.promise();
        }
        
        /**
         * go to next screen
         */
        nextScreen(): void {
            $("#screenA").css("display", "none");
            $("#screenB").css("display", "");
            $("#screenB").ready(function() {
                $(".func-btn").css("visibility", "visible");
            });
        }
        
        /**
         * back to previous screen
         */
        backScreen(): void {
            $("#screenB").css("display", "none");
            $("#screenA").css("display", "");
            $(".func-btn").css("visibility", "hidden");
        }
    }
};
