module kcp004.a.viewmodel {
    
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    import TreeType = kcp.share.tree.TreeType;
    
    export class ScreenModel {
        
        selectedCode: KnockoutObservable<string>;
        baseDate: KnockoutObservable<Date>;
        selectedCodeNoDisp: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservable<any>;
        multiSelectedCodeNoDisp: KnockoutObservable<any>;
        
        treeComponentOptionMultiple: TreeComponentOption;
        treeComponentOption: TreeComponentOption;
        treeComponentOptionMultipleNoDispAlready: TreeComponentOption;
        treeComponentOptionNoDispAlready: TreeComponentOption;
        
        constructor() {
            let self = this;
            
            self.baseDate = ko.observable(new Date());
            self.selectedCode = ko.observable('001');
            self.selectedCodeNoDisp = ko.observable('002');
            self.multiSelectedCode = ko.observableArray(['001', '002']);
            self.multiSelectedCodeNoDisp = ko.observableArray(['002', '003']);
            self.treeComponentOptionMultiple = {
                isShowAlreadySet: true,
                isMultiSelect: true,
                treeType: TreeType.WORK_PLACE,
                selectedCode: self.multiSelectedCode,
                baseDate: self.baseDate,
                isDialog: false,
                alreadySettingList: ko.observableArray([
                    {code: '001', settingType: 0},
                    {code: '001001', settingType: 1},
                    {code: '002002', settingType: 2},
                    {code: '001001001', settingType: 1},
                    {code: '003002001', settingType: 2}
                ])
            }
            self.treeComponentOption = {
                isShowAlreadySet: true,
                isMultiSelect: false,
                treeType: TreeType.WORK_PLACE,
                selectedCode: self.selectedCode,
                baseDate: self.baseDate,
                isDialog: true,
                alreadySettingList: ko.observableArray([
                    {code: '001', settingType: 0},
                    {code: '001001', settingType: 1},
                    {code: '002002', settingType: 2},
                    {code: '001001001', settingType: 1},
                    {code: '003002001', settingType: 2}
                ])
            }
            self.treeComponentOptionMultipleNoDispAlready = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                treeType: TreeType.WORK_PLACE,
                selectedCode: self.multiSelectedCodeNoDisp,
                baseDate: self.baseDate,
                isDialog: false,
                alreadySettingList: ko.observableArray([
                    {code: '001', settingType: 0},
                    {code: '001001', settingType: 1},
                    {code: '002002', settingType: 2},
                    {code: '001001001', settingType: 1},
                    {code: '003002001', settingType: 2}
                ])
            }
            
            self.treeComponentOptionNoDispAlready = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                treeType: TreeType.WORK_PLACE,
                selectedCode: self.selectedCodeNoDisp,
                baseDate: self.baseDate,
                isDialog: true,
                alreadySettingList: ko.observableArray([
                    {code: '001', settingType: 0},
                    {code: '001001', settingType: 1},
                    {code: '002002', settingType: 2},
                    {code: '001001001', settingType: 1},
                    {code: '003002001', settingType: 2}
                ])
            }
        }
        
        setAlreadyCheck() {
//            this.treeComponentOptionMultiple.alreadySettingList.push({"code": "01", "isAlreadySetting": true});
//            this.treeComponentOption.alreadySettingList.push({"code": "02", "isAlreadySetting": true});
        }
    }
}