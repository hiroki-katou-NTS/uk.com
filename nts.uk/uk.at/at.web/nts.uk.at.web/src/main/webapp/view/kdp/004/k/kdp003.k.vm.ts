module kdp003.k.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];


    export class ViewModel {

        multipleSelectMode: KnockoutObservable<boolean> = ko.observable(false);
        singleSelectMode: KnockoutObservable<boolean> = ko.observable(true);

        multiSelectedId: KnockoutObservable<any>;
        baseDate: KnockoutObservable<Date>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        treeGrid: TreeComponentOption;
                
        jsonData: KnockoutObservable<string>;
        rowSelected: KnockoutObservable<RowSelection>;
        isBindingTreeGrid: KnockoutObservable<boolean>;

        constructor() {
            let self = this;

            self.baseDate = ko.observable(new Date());
            self.multiSelectedId = ko.observableArray([]);
            self.alreadySettingList = ko.observableArray([]);
            self.treeGrid = {
                isShowAlreadySet: false,
                isMultipleUse: false,
                isMultiSelect: false,
                startMode: 0,//WORKPLACE,
                selectedId: self.multiSelectedId,
                baseDate: self.baseDate,
                selectType: 4, // No select
                isShowSelectButton: true,
                isDialog: false,
                alreadySettingList: self.alreadySettingList,
                maxRows: 10,
                tabindex: 1,
                systemType: 2,
                restrictionOfReferenceRange: false

            };
            
            self.jsonData = ko.observable('');
            self.rowSelected = ko.observable(new RowSelection('', ''));
            self.isBindingTreeGrid = ko.observable(true);
            
        }
        
        selectWorkPlace() {
            let self = this;
            if(_.size(self.multiSelectedId()) == 0){
                nts.uk.ui.dialog.error({ messageId: "Msg_643" });
                return;
            }
            setShared('KDP003K_RETURN', {listWorkPlaceId: self.multiSelectedId() , status: 'ok'});
            console.log(getShared('KDP003K_RETURN'));
            
            // nhay sang man A
        }
        
        cancel() {
            let self = this;
            setShared('KDP003K_RETURN', {status: 'cancel'});
            console.log(getShared('KDP003K_RETURN'));
            close();
        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            self.resetSelectedWorkplace();
            self.reloadTreeGrid().done(() => {
                self.getSelectedData();
            });
            dfd.resolve();
            return dfd.promise();
        }

        public getSelectedData() {
            let self = this;
            if (!self.isBindingTreeGrid()) {
                return;
            }
            let data = $('#tree-grid').getRowSelected();
            self.rowSelected().workplaceId(data.length > 0 ? data.map(item => item.id).join(", ") : '');
            self.rowSelected().workplaceCode(data.length > 0 ? data.map(item => item.code).join(", ") : '');
        }
        
        private setTreeData() {
            let self = this;
            self.treeGrid = {
                isShowAlreadySet: false,
                isMultipleUse: true,
                isMultiSelect: false,
                startMode: 0,//WORKPLACE,
                selectedId: self.multiSelectedId,
                baseDate: self.baseDate,
                selectType: 4, // No select
                isShowSelectButton: true,
                isDialog: false,
                alreadySettingList: self.alreadySettingList,
                maxRows: 10,
                tabindex: 1,
                systemType: 2,
                restrictionOfReferenceRange: false
            };
        }
        
        private reloadTreeGrid() : JQueryPromise<void>  {
            let self = this;
            let dfd = $.Deferred<void>();
            self.setTreeData();
            $('#tree-grid').ntsTreeComponent(self.treeGrid).done(() => {
                $('#tree-grid').focusTreeGridComponent();
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        private resetSelectedWorkplace() {
            let self = this;
            self.multiSelectedId([]);
        }

    }
    /**
   * Class Row Selection
   */
    export class RowSelection {
        workplaceId: KnockoutObservable<string>;
        workplaceCode: KnockoutObservable<string>;

        constructor(workplaceId: string, workplaceCode: string) {
            let self = this;
            self.workplaceId = ko.observable(workplaceId);
            self.workplaceCode = ko.observable(workplaceCode);
        }
    }
}

