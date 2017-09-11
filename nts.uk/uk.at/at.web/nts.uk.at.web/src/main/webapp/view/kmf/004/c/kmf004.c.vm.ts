module nts.uk.at.view.kmf004.c {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class ScreenModel {
            //search
            baseDate: KnockoutObservable<Date> = ko.observable(new Date());

            //Grid data
            columns: KnockoutObservable<any>;
            singleSelectedCode: KnockoutObservable<any>;
            items: KnockoutObservableArray<ItemModel>;

            model: KnockoutObservable<BonusPaySetting> = ko.observable(new BonusPaySetting({ id: '', name: '' }));
            
            code: KnockoutObservable<string>;
            editMode: KnockoutObservable<boolean>;
            name: KnockoutObservable<string>;
            
            constructor() {
                let self = this,
                   
                model = self.model();

                //Grid data
                self.items = ko.observableArray([]);
                
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KMF004_7"), prop: 'code', width: 50 },
                    { headerText: nts.uk.resource.getText("KMF004_8"), prop: 'name', width: 200, formatter: _.escape }
                ]);
                
                self.singleSelectedCode = ko.observable("");
                
                self.code = ko.observable("");
                self.editMode = ko.observable(true);  
                self.name = ko.observable("");          
            }

            start() {
                let self = this;
                
            }
        }


        interface IBonusPaySetting {
            id: string;
            name: string;
            wid?: string; // workplace id
            wname?: string; // workplace name
        }

        class BonusPaySetting {
            id: KnockoutObservable<string> = ko.observable('');
            name: KnockoutObservable<string> = ko.observable('');
            wid: KnockoutObservable<string> = ko.observable('');
            wname: KnockoutObservable<string> = ko.observable('');

            constructor(param: IBonusPaySetting) {
                let self = this;

                self.id(param.id);
                self.name(param.name);
                self.wid(param.wid || '');
                self.wname(param.wname || '');

                self.id.subscribe(x => {
                    let view = __viewContext.viewModel && __viewContext.viewModel.tabView,
                        acts: any = view && _.find(view.tabs(), (t: any) => t.active());
                    if (acts && acts.id == 'H') {
                        view.removeAble(!!x);
                    }
                });
            }
        }

        interface ITreeGrid {
            treeType: number;
            selectType: number;
            isDialog: boolean;
            isMultiSelect: boolean;
            isShowAlreadySet: boolean;
            isShowSelectButton: boolean;
            baseDate: KnockoutObservable<any>;
            selectedWorkplaceId: KnockoutObservable<any>;
            alreadySettingList: KnockoutObservableArray<any>;
        }
    }
}