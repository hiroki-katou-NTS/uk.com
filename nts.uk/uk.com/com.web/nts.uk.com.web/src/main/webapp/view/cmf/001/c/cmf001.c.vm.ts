/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
/// <reference path="../../../../lib/nittsu/nts.uk.com.web.nittsu.bundles.d.ts" />

module nts.uk.com.cmf001.c {

    let datasource = {
        importableItem: {
            init() {
                return (<any> ko).mapping.fromJS({
                    name: null,
                    type: null,
                    constraint: null,
                    required: null,
                });
            }
        },

        itemMapping: {
            init() {
                let res = {
                    source: null,
                    settingCode: null,
                    itemNo: null,
                    type: null,
                    revisingValue: {
                        usePadding: null,
                        paddingLength: null,
                        paddingMethod: null,
                        useCodeConvert: null,
                        codeConvert: {
                            importWithoutSetting: null,
                            convertDetailsText: "",
                        },
                        isDecimalization: null,
                        decimalizationLength: null,
                        timeHourlySegment: null,
                        timeBaseNumber: null,
                        timeDelimiter: null,
                        timeRounding: null,
                        dateFormat: null,
                    },
                };

                return (<any> ko).mapping.fromJS(res);
            },

        }
    }

    function renderConfiguredIcon(configured) {
        if (configured === "true") {
            return '<i data-bind="ntsIcon: { no: 78, width: 20, height: 20 }"></i>';
        } else {
            return '';
        }
    }

    @bean()
    export class ViewModel extends ko.ViewModel {

        settingCode = "001";

        items: KnockoutObservableArray<any> = ko.observableArray([]);

        itemsColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: "NO", key: "itemNo", width: 0, hidden: true },
            { headerText: "名称", key: "name", width: 200 },
            { headerText: "", key: "alreadyDetail", width: 40, formatter: renderConfiguredIcon },
        ]);

        selectedItemNo: KnockoutObservable<number> = ko.observable(null);

        currentItem: any = ko.observable();

        constructor() {
            super();

            this.currentItem({
                def: datasource.importableItem.init(),
                selectedMappingType: ko.observable(null),
                mappingTypes: [
                    { code: "CSV", name: "CSV" },
                    { code: "固定値", name: "固定値" },
                ],
                csvMapping: datasource.itemMapping.init(),
                fixedMapping: ko.observable(),
            });

            this.selectedItemNo.subscribe(v => this.itemSelected(v));
        }

        mounted() {
            this.loadSetting().done(() => {
                $("body").removeClass("ko-cloak");
            });
        }

        loadSetting() {
            let dfd = $.Deferred();

            let path = "exio/input/setting/find/" + this.settingCode;
            this.$ajax(path).done(res => {
                this.items.removeAll();
                res.layouts.forEach(layout => {
                    this.items.push(layout);
                });

                dfd.resolve();
            });

            return dfd.promise();
        }

        itemSelected(itemNo) {

            let selectedItem = this.items().filter(e => e.itemNo == itemNo)[0];

            this.currentItem().def.name(selectedItem.name);
            this.currentItem().def.type(selectedItem.type);
            this.currentItem().selectedMappingType(selectedItem.source);

            this.loadImportableItem();
            this.loadReviseItem();
        }

        loadImportableItem() {

            let path = "/screen/com/cmf/cmf001/importable-item"
                + "/" + this.settingCode
                + "/" + this.selectedItemNo();
            
            this.$ajax(path).done(res => {

                this.currentItem().def.required(res.required);

                if (res.constraint !== null) {
                    __viewContext.primitiveValueConstraints[res.constraint.name] = res.constraint;
                    let constraintText = (<any> util).getConstraintMes(res.constraint.name) + "　";
                    if (res.constraint.domainType === "Enum") {
                        constraintText = "整数 " + constraintText;
                    }

                    this.currentItem().def.constraint(constraintText);
                } else {
                    this.currentItem().def.constraint("");
                }

            });
        }

        loadReviseItem() {

            let path = "/exio/input/setting/assembly/revise/reviseitem/get"
                + "/" + this.settingCode
                + "/" + this.selectedItemNo();

            this.$ajax(path).done(res => {
                let mapping = this.currentItem().csvMapping;

                (<any> ko).mapping.fromJS(res.revisingValue, {}, mapping.revisingValue);

                mapping.revisingValue.codeConvert.convertDetailsText(
                    res.revisingValue.codeConvert.details
                        .map(d => d.before + "," + d.after)
                        .join("\r\n")
                );
            });
        }
    }

}