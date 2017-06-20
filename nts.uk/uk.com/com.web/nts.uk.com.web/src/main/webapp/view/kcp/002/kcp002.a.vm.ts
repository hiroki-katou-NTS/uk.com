module kcp002.a.viewmodel {
    import ComponentOption = kcp.share.list.ComponentOption;
    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    export class ScreenModel {
        selectedCode: KnockoutObservable<string>;
        selectedCodeNoSetting: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservable<any>;
        multiSelectedCodeNoSetting: KnockoutObservable<any>;
        listComponentOption: ComponentOption;
        listComponentOptionMulti: ComponentOption;
        listComponentNoneSetting: ComponentOption;
        listComponentMultiNoneSetting: ComponentOption;

        constructor() {
            this.selectedCode = ko.observable('0000000002');
            this.selectedCodeNoSetting = ko.observable(null);
            this.multiSelectedCodeNoSetting = ko.observableArray(['0000000002', '0000000004']);
            this.multiSelectedCode = ko.observableArray([]);
            this.listComponentOption = {
                isShowAlreadySet: true, // is show already setting column.
                isMultiSelect: false, // is multiselect.
                /**
                 * 1- Employment list.
                 * 2- ???.
                 * 3- Job_title list.
                 * 4- Employee list.
                 */
                listType: ListType.Classification,
                /**
                 * Selected value:
                 * Return type is Array<string> while multiselect.
                 * Return type is String while select.
                 */
                selectType : SelectType.SELECT_BY_SELECTED_CODE;
                selectedCode: this.selectedCode,
                isDialog: true,
                alreadySettingList: ko.observableArray([{ code: '0000000001', isAlreadySetting: true }])
            }
            $('#empt-list-setting').ntsListComponent(this.listComponentOption);


            this.listComponentOptionMulti = {
                isShowAlreadySet: true,
                isMultiSelect: true,
                listType: ListType.Classification,
                selectType : SelectType.SELECT_ALL;
                selectedCode: this.multiSelectedCode,
                isDialog: true,
                alreadySettingList: ko.observableArray([{ code: '0000000001', isAlreadySetting: true }, { code: '0000000002', isAlreadySetting: true }])
            }
            $('#empt-list-multi-setting').ntsListComponent(this.listComponentOptionMulti);

            this.listComponentNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.Classification,
                selectType : SelectType.SELECT_FIRST_ITEM;
                selectedCode: this.selectedCodeNoSetting,
                isDialog: true
                //                alreadySettingList: ko.observableArray([{code: '01', isAlreadySetting: true}, {code: '02', isAlreadySetting: true}])
            }
            $('#empt-list-noSetting').ntsListComponent(this.listComponentNoneSetting);


            this.listComponentMultiNoneSetting = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.Classification,
                selectType : SelectType.NO_SELECT;
                selectedCode: this.multiSelectedCodeNoSetting,
                isDialog: true
                //                alreadySettingList: ko.observableArray([{code: '01', isAlreadySetting: true}, {code: '02', isAlreadySetting: true}])
            }
            $('#empt-list-multiSelect-noSetting').ntsListComponent(this.listComponentMultiNoneSetting);

        }
        setAlreadyCheck() {
            this.listComponentOption.alreadySettingList.push({ "code": "0000000002", "isAlreadySetting": true });
        }
    }
}