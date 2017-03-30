// TreeGrid Node
module qpp014.a.viewmodel {
    export class ScreenModel {
        //
        viewmodelb = new qpp014.b.viewmodel.ScreenModel();
        viewmodeld = new qpp014.d.viewmodel.ScreenModel();
        viewmodelg = new qpp014.g.viewmodel.ScreenModel();
        viewmodelh = new qpp014.h.viewmodel.ScreenModel();

        //viewmodel A
        a_SEL_001_items: KnockoutObservableArray<PayDayProcessing>;
        a_SEL_001_itemSelected: KnockoutObservable<any>;

        constructor() {
            $('.func-btn').css('display', 'none');
            $('#screenB').css('display', 'none');
            let self = this;
            

            //viewmodel A
            self.a_SEL_001_items = ko.observableArray([
                new PayDayProcessing('基本給1', '基本給'),
                new PayDayProcessing('基本給2', '役職手当'),
                new PayDayProcessing('0003', '基本給')
            ]);
            self.a_SEL_001_itemSelected = ko.observable('0003');

        }
        nextScreen() {
            $("#screenA").css("display", "none");
            $("#screenB").css("display", "");
            $("#screenB").ready(function() {
                $(".func-btn").css("display", "");
            });
        }
        backScreen() {
            $("#screenB").css("display", "none");
            $("#screenA").css("display", "");
            $(".func-btn").css("display", "none");
        }
        
    }

    // Pay day processing
    export class PayDayProcessing {
        companyCode: string;
        processingNumber: number;
        processingName: string;
        displaySet: number;
        currentProcessing: number;
        bonusAttribute: number;
        bonusCurrentProcessing: number;
        
        constructor(companyCode: string, processingNumber: number, processingName: string,
            displaySet: number, currentProcessing: number, bonusAttribute: number, bonusCurrentProcessing: number) {
            this.companyCode = companyCode;
            this.processingNumber = processingNumber;
            this.processingName = processingName;
            this.displaySet = displaySet;
            this.currentProcessing = currentProcessing;
            this.bonusAttribute = bonusAttribute;
            this.bonusCurrentProcessing = bonusCurrentProcessing;
        }
    }
    
    
    

};
