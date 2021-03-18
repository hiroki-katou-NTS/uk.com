module nts.uk.com.view.kcp016.a.viewmodel {

    const PATH = {
        getList: "screen/at/ksm008/alarm_contidion/list"
    };

    const template = `
    <div id="kcp016-component" 
        class="panel" 
        style="display: inline-block;" 
        data-bind="css: {
            ntsPanel: !onDialog(), 
            'caret-right': !onDialog(), 
            'caret-background': !onDialog()
        }">
            <table id="single-list" 
                data-bind="ntsGridList: {
                    name: $i18n('KCP016_2'),								
                    rows: 10,
                    dataSource: items,
                    primaryKey: 'code',
                    columns: columns,
                    multiple: multiple(),
                    value: value
                }"/>
    </div>
    `;

    @component({
        name: 'kcp016-component',
        template: template
    })
    class ViewModel extends ko.ViewModel {
        columns: KnockoutObservableArray<any>;
        multiple: KnockoutObservable<boolean>;
        onDialog: KnockoutObservable<boolean>;
        selectType: KnockoutObservable<SelectType>;

        items: KnockoutObservableArray<any> = ko.observableArray([]);
        value: KnockoutObservable<string> | KnockoutObservableArray<string>;

        created(params: Params) {
            const vm = this;
            if (params) {
                vm.multiple = ko.isObservable(params.multiple) ? params.multiple : ko.observable(!!params.multiple);
                vm.onDialog = ko.isObservable(params.onDialog) ? params.onDialog : ko.observable(!!params.onDialog);
                vm.selectType = ko.isObservable(params.selectType) ? params.selectType : ko.observable(!!params.selectType);
                if (vm.multiple()) {
                    vm.value = ko.isObservable(params.selectedValue) ? params.selectedValue : ko.observableArray([]);
                } else {
                    vm.value = ko.isObservable(params.selectedValue) ? params.selectedValue : ko.observable(null);
                }
            } else {
                vm.multiple = ko.observable(false);
                vm.onDialog = ko.observable(false);
                vm.value = ko.observable(null);
                vm.selectType = ko.observable(SelectType.NO_SELECT);
            }
            vm.columns = ko.observableArray([
                { headerText: vm.$i18n("KCP016_3"), key: 'code', width: 100 },
                { headerText: vm.$i18n("KCP016_3"), key: 'name', width: 150 },
            ]);
        }

        mounted() {
            const vm = this;
            vm.$blockui("show").then(() => {
                return vm.$ajax("at", PATH.getList);
            }).done(data => {
                vm.items(data.map((i: any) => ({code: i.code, name: i.name})));
                if (vm.multiple()) {
                    if (vm.selectType() == SelectType.SELECT_ALL) vm.value(data.map(i => i.code));
                    else if (vm.selectType() == SelectType.SELECT_FIRST_ITEM && !_.isEmpty(data)) vm.value([data[0].code]);
                    else if (vm.selectType() == SelectType.NO_SELECT) vm.value([]);
                } else {
                    if (vm.selectType() == SelectType.SELECT_FIRST_ITEM && !_.isEmpty(data)) vm.value(data[0].code);
                    else if (vm.selectType() == SelectType.NO_SELECT) vm.value(null);
                }
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }
    }

    interface Params {
        onDialog?: boolean | KnockoutObservable<boolean>; // default: false
        multiple?: boolean | KnockoutObservable<boolean>; // default: false
        selectType?: SelectType; // default: 4 (NO_SELECT)
        selectedValue?: KnockoutObservable<string> | KnockoutObservableArray<string>; // required when selectType = 1 (SELECT_BY_SELECTED_CODE)
    }

    class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

}