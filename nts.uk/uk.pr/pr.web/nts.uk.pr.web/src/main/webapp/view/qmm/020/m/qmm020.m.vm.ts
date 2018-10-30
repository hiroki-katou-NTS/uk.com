module nts.uk.pr.view.qmm020.m.viewmodel {
    import model = qmm020.share.model;
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {
        items: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any> = ko.observable();
        constructor(){
            var str = ['a0', 'b0', 'c0', 'd0'];
            for(var j = 0; j < 4; j++) {
                for(var i = 1; i < 10; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    this.items.push(new model.ItemModel(code,code,code));
                }
            }
            this.columns = ko.observableArray([
                { headerText: getText('QMM020_26'), prop: 'code', width: 50 },
                { headerText: getText('QMM020_27'),  prop: 'code', width: 110 },
                { headerText: getText('QMM020_81'), prop: 'description', width: 110 }
            ]);
        }
        submit(){

        }
        cancel(){
            close();
        }
    }

}