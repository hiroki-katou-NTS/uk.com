module nts.uk.pr.view.qmm011.d {
    
    import option = nts.uk.ui.option;
    import TypeHistory = nts.uk.pr.view.qmm011.a.service.model.TypeHistory;
    
    export module viewmodel {
        
        export class ScreenModel {
            
            selectModel: KnockoutObservableArray<BoxModel>;
            enable: KnockoutObservable<boolean>;
            textEditorOption: KnockoutObservable<any>;
            historyId: KnockoutObservable<string>;
            historyStart: KnockoutObservable<string>;
            historyTakeover: KnockoutObservable<boolean>;
            historyEnd: KnockoutObservable<string>;
            selectedId: KnockoutObservable<number>;
            typeHistory: KnockoutObservable<number>;

            constructor() {
                var self = this;
                self.enable = ko.observable(true);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.historyStart = ko.observable('');
                self.historyEnd = ko.observable('9999/12');
                self.selectedId = ko.observable(1);
                var isEmpty = nts.uk.ui.windows.getShared("isEmpty");
                if (!isEmpty) {
                    var historyStart = nts.uk.ui.windows.getShared("historyStart");
                    self.selectModel = ko.observableArray<BoxModel>([
                        new BoxModel(1, '最新の履歴 (' + historyStart + ') から引き継ぐ'),
                        new BoxModel(2, '初めから作成する')
                    ]);
                } else {
                    self.selectModel = ko.observableArray<BoxModel>([
                        new BoxModel(1, '最新の履歴（N）から引き継ぐ'),
                        new BoxModel(2, '初めから作成する')
                    ]);
                }
            }
            
            fwaddHistoryInfo() {
                var self = this;
                var addHistoryInfoModel: AddHistoryInfoModel;
                addHistoryInfoModel = new AddHistoryInfoModel();
                addHistoryInfoModel.typeModel = self.selectedId();
                addHistoryInfoModel.historyStart = self.historyStart();
                nts.uk.ui.windows.setShared("addHistoryInfoModel", addHistoryInfoModel);
                nts.uk.ui.windows.close();
            }
        }

        export class BoxModel {
            
            id: number;
            name: string;
            
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
        
        export class AddHistoryInfoModel {
            typeModel: number;// add new, add before
            historyStart: string;
        }
    }
}