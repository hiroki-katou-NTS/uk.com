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

            $('.func-btn').css('visibility', 'hidden');
            $('#screenB').css('display', 'none');

            let self = this;


            //viewmodel A
            self.a_SEL_001_items = ko.observableArray([]);
            self.a_SEL_001_itemSelected = ko.observable(1);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.findAll();
            dfd.resolve();
            return dfd.promise();
        }

        findAll(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qpp014.a.service.findAll("", 0)
                .done(function(data) {
                    self.a_SEL_001_items(data);
                    dfd.resolve();
                }).fail(function(res) {
                    dfd.reject(res);
                });
            return dfd.promise();
        }

        nextScreen(): void {
            $("#screenA").css("display", "none");
            $("#screenB").css("display", "");
            $("#screenB").ready(function() {
                $(".func-btn").css("visibility", "visible");
            });
        }

        backScreen(): void {
            $("#screenB").css("display", "none");
            $("#screenA").css("display", "");
            $(".func-btn").css("visibility", "hidden");
        }
    }
};
