module nts.uk.at.view.kdl049.a.test.viewmodel {
    
    export class ScreenModel {
        
        treeGrid: TreeComponentOption;
        baseDate: KnockoutObservable<Date> = ko.observable(new Date());
        selectedWorkplaceId: KnockoutObservable<string> = ko.observable("");
        workplaceName: KnockoutObservable<string> = ko.observable("");
        wplDatas: KnockoutObservableArray<any> = ko.observableArray([]);
        dateSelected: KnockoutObservable<string>;
        constructor() {
            var self = this;
           
        }
        
        startPage(): JQueryPromise<any> {
            let self = this;
            self.dateSelected = ko.observable('20200101');
            let dfd = $.Deferred();
            self.treeGrid = {
                    isMultipleUse: true,
                    isMultiSelect: false,
                    treeType: 1,
                    selectedId: self.selectedWorkplaceId,
                    baseDate: self.baseDate,
                    selectType: 4,
                    isShowSelectButton: false,
                    isDialog: false,
                    maxRows: 15,
                    tabindex: 1,
                    systemType: 2

                };
            $('#tree-grid').ntsTreeComponent(self.treeGrid).done(() => {
                self.wplDatas($('#tree-grid').getDataList());
            });
           
                dfd.resolve();
           
            return dfd.promise();
        }
        
        openKDL049() {
            let self = this;
            if (self.selectedWorkplaceId() != undefined) {
                let result = _.find(self.wplDatas(), function(n) { 
                        return n.id == self.selectedWorkplaceId(); 
                });
                self.selectedWorkplaceId(result.id);
                self.workplaceName(result.name);
            }
            let param = {
                dateSelected: self.dateSelected(), 
                workplace: self.selectedWorkplaceId() != undefined ? 
                        { workPlaceID: self.selectedWorkplaceId(), targetOrgWorkplaceName: self.workplaceName()} : null
                }
            nts.uk.ui.windows.setShared('KDL049', param);
            nts.uk.ui.windows.sub.modal( "/view/kdl/049/a/index.xhtml" );
            }
    }
}