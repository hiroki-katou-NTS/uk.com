module nts.uk.at.kha003.a {

    const API = {
        //TODO api path
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        manHour: CodeName = new CodeName('', '');
        // A6_1_1 radio button
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        // A6_2_2 radio button
        itemListA622: KnockoutObservableArray<any>;
        selectedIdA622: KnockoutObservable<number>;
        enableA622: KnockoutObservable<boolean>;
        //A7_1
        isA71Checked: KnockoutObservable<boolean>;
        isA71Enable: KnockoutObservable<boolean>;
        //A7_2
        isA72Enable: KnockoutObservable<boolean>;

        layoutSettings: KnockoutObservableArray<any>;

        constructor() {
            super();
            const vm = this;
            vm.currentCode = ko.observable();
            vm.currentCodeList = ko.observableArray([]);
            vm.items = ko.observableArray([]);
            vm.layoutSettings = ko.observableArray([]);
            for (var i = 1; i < 51; i++) {
                vm.items.push(new ItemModel('code' + i, 'name' + i));
            }
            vm.manHour.code('111');
            vm.manHour.name('name');
            vm.itemList = ko.observableArray([
                new BoxModel(1, this.$i18n('KHA003_28')),
                new BoxModel(2, this.$i18n('KHA003_29')),
            ]);
            vm.selectedId = ko.observable(1);
            vm.enable = ko.observable(true);
            vm.itemListA622 = ko.observableArray([
                new BoxModel(1, this.$i18n('KHA003_32') + this.$i18n('KHA003_35')),
                new BoxModel(2, this.$i18n('KHA003_33') + this.$i18n('KHA003_36')),
                new BoxModel(3, this.$i18n('KHA003_34') + this.$i18n('KHA003_37')),
            ]);
            vm.selectedIdA622 = ko.observable(1);
            vm.enableA622 = ko.observable(true);
            vm.isA71Checked = ko.observable(true);
            vm.isA71Enable = ko.observable(true);
            vm.isA72Enable = ko.observable(true);

        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            var itemId = '';
            var itemCount = 0;
            $("#appen-area-one .pannel_padding").draggable({
                helper: function (e) {
                    var html = e.target
                    itemId = html.id;
                    itemCount = $('#append_area .cell').length;
                    if (itemCount <= 3) {
                        return '<div class="panel panel-gray-bg item_a_6_4_to_67">\n' +
                            '                                            <button class="button_top_right_corner"><i class="icon icon-close"></i></button>\n' +
                            '                                            <span class="label" style="display: table;margin: 71px auto;">' + html.innerText + '</span>\n' +
                            '                                        </div>';
                    }
                },
                stop: function (e, ui) {
                    var html = ui.helper;
                    if (itemCount <= 3) {
                        $(this).css({'pointer-events': 'none'});
                        $(this).children().removeClass('bacg-active').addClass('bacg-inactive');
                        $('#append_area').append('<div class="cell valign-center">\n' +
                            '                                        <div style="background-color: #e7d3193b" class="panel  item_a_6_4_to_67">\n' +
                            '                                            <button id="' + itemId + '" class="button_top_right_corner"><i class="icon icon-close"></i></button>\n' +
                            '                                            <span class="label layout-setting" style="display: table;margin: 71px auto;">' + $(this).children().html() + '</span>\n' +
                            '                                        </div>\n' +
                            '                                    </div>');
                    }
                    if (itemCount >= 3) {
                        $('#append_note').hide();
                    }
                    matchWidth();
                }
            });

            function matchWidth() {
                $('#free_area').css("width", $('#appen_parent').width());
            }

            $(document).ready(function () {
                $('#append_area').on('click', ".button_top_right_corner", function (e) {
                    var id = $(this).attr('id');
                    $('#' + id).removeClass('bacg-inactive').addClass('bacg-active');
                    $('#' + id).parent().css({'pointer-events': 'auto'});
                    $(this).parent().parent().remove();
                    if ($('#append_area .cell').length <= 3) {
                        $('#append_note').show();
                    }
                    matchWidth();
                });
            });
        }

        mounted() {
            const vm = this;
        }

        /**
         * create new button
         *
         * @author rafiqul.islam
         * */
        clickNewButton() {
            const vm = this;
            alert("new button is clicked")
        }

        /**
         * registration
         *
         * @author rafiqul.islam
         * */
        clickRegistrationButton() {
            const vm = this;
            alert("Registration button is clicked")
        }

        /**
         * duplicate
         *
         * @author rafiqul.islam
         * */
        clickDuplicateButton() {
            const vm = this;
            let shareData={
                code:vm.manHour.code(),
                name:vm.manHour.name()
            }
            vm.$window.storage('kha003ERequiredData', shareData).then(() => {
                vm.$window.modal("/view/kha/003/e/index.xhtml").then(() => {

                });
            });
        }

        /**
         * click delete
         *
         * @author rafiqul.islam
         * */
        clickDeleteButton() {
            const vm = this;
            alert("delete button is clicked")
        }

        /**
         * output all
         *
         * @author rafiqul.islam
         * */
        clickOutputAllButton() {
            const vm = this;
            alert("output all button is clicked")
        }

        /**
         * click run button
         *
         * @author rafiqul.islam
         * */
        clickRunButton() {
            const vm = this;
            var c21 = '';
            var c31 = '';
            var c41 = '';
            var c51 = '';
            $('.layout-setting').each(function (i, obj) {
                if (i == 0) {
                    c21 = obj.innerHTML;
                }
                if (i == 1) {
                    c31 = obj.innerHTML;
                }
                if (i == 2) {
                    c41 = obj.innerHTML;
                }
                if (i == 3) {
                    c51 = obj.innerHTML;
                }
            });
            let shareData={
                c21:c21,
                c31:c31,
                c41:c41,
                c51:c51,
            }
            vm.$window.storage('kha003AShareData', shareData).then(() => {
                vm.$window.modal("/view/kha/003/b/index.xhtml").then(() => {

                });
            });
        }
    }

    class BoxModel {
        id: number;
        name: string;

        constructor(id: any, name: any) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class CodeName {
        /** コード */
        code: KnockoutObservable<string>;
        /** 名称 */
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }

    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}


