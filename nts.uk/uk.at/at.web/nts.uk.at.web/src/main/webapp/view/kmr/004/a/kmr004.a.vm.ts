/// <reference path="../../../../../../../../../../uk.com/com.web/nts.uk.com.web/src/main/webapp/view/kcp/share/tree.ts" />

module nts.uk.at.view.kmr004.a {
    import getText = nts.uk.resource.getText;
    import tree = kcp.share.tree;

    @bean()
	export class KMR004AViewModel extends ko.ViewModel {

        dateValue: KnockoutObservable<any> = ko.observable({ startDate: '2018/05/02', endDate: '2018/05/02' });

        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: KnockoutObservable<any>;

        multiSelectedId: KnockoutObservable<any>;
        baseDate: KnockoutObservable<Date>;
        alreadySettingList: KnockoutObservableArray<tree.UnitAlreadySettingModel>;
        treeGrid: tree.TreeComponentOption;

        constructor() {
            super();
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: getText('KMR004_8') },
                { code: '2', name: getText('KMR004_9') }
            ]);
            self.selectedRuleCode = ko.observable('1');

            // tree grid
            self.baseDate = ko.observable(new Date());
            self.multiSelectedId = ko.observableArray([]);
            self.alreadySettingList = ko.observableArray([]);
            self.treeGrid = {
                isShowAlreadySet: true,
                isMultipleUse: true,
                isMultiSelect: true,
                startMode: tree.StartMode.WORKPLACE,
                selectedId: self.multiSelectedId,
                baseDate: self.baseDate,
                selectType: tree.SelectionType.SELECT_FIRST_ITEM,
                isShowSelectButton: true,
                isDialog: false,
                alreadySettingList: self.alreadySettingList,
                maxRows: 10,
                tabindex: 1,
                systemType: 2
            }
			
			$('#tree-grid').ntsTreeComponent(self.treeGrid);
        }

        created() {
            const vm = this;

            _.extend(window, { vm });
        }

        mounted() {
            var vm = this;
        }
    }
}
