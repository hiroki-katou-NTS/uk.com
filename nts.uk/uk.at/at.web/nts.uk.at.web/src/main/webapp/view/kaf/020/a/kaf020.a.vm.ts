///<reference path="../../../../lib/nittsu/viewcontext.d.ts"/>
module nts.uk.at.kaf020.a {
    const PATH_API = {
        initScreen: 'screen/at/kaf020/a/get',
    }

    @bean()
    export class Kaf020AViewModel extends ko.ViewModel {
        optionalItemAppSet: KnockoutObservableArray<OptionalItemAppSet> = ko.observableArray([]);
        constructor(props: any) {
            super();
        }

        initScreen() {
            const vm = this;
            vm.$ajax(PATH_API.initScreen).done((data: Array<OptionalItemAppSet>) => {
                if (data.length == 0) {
                    vm.$dialog.error({messageId: "Msg_1694"});
                } else if (data.length == 1) {
                    vm.$jump('/view/kaf/020/b/index.xhtml')
                } else {
                    vm.optionalItemAppSet(data);
                }
            });
        }


        check(){
            return this;
        }

        created() {
            const vm = this
            vm.initScreen();
        }

        mounted() {
            $("#fixed-table").ntsFixedTable({height: 480, width: 1200});
            setTimeout(() => {
                $("#pg-name").text("KAF020A " + nts.uk.resource.getText("KAF020_1"));
            }, 300);
        }


        detail(parent: any) {
            parent.$jump('../b/index.xhtml');
        }
    }


    interface OptionalItemAppSet {
        code: string,
        name: string,
        useAtr: number,
        note: string,
        settingItems: Array<OptItemSet>;
    }

    interface OptItemSet {
        no: number,
        dispOrder: number
    }
}