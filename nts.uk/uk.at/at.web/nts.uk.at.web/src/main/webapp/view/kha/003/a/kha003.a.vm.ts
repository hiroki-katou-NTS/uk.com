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

        constructor() {
            super();
            const vm = this;
            vm.currentCode = ko.observable();
            vm.currentCodeList = ko.observableArray([]);
            vm.items = ko.observableArray([]);
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

            $("#appen-area-one .pannel_padding").draggable({
                helper: function (e) {
                    return e.target;
                },
                stop: function (e, ui) {
                    var html = ui.helper.html();
                    ui.helper.remove();
                    $('#append_area').append('<div class="cell valign-center">\n' +
                        '                                        <div class="panel panel-gray-bg item_a_6_4_to_67">\n' +
                        '                                            <button  class="button_top_right_corner"><i class="icon icon-close"></i></button>\n' +
                        '                                            <span class="label" style="display: table;margin: 71px auto;">' + html + '</span>\n' +
                        '                                        </div>\n' +
                        '                                    </div>');
                }
            });

            $(document).ready(function () {
                $('#append_area').on('click', ".button_top_right_corner", function (e) {
                    console.log($(this).next('.label').html());
                    /*$('#appen-area-one').append('<div class="cell valign-center pannel_padding">\n' +
                        '                                    <div class="panel panel-gray-bg panel_common">'+$(this).next('.label').html()+'</div>\n' +
                        '                                </div>');*/
                    var html = '<div class="cell valign-center pannel_padding">\n' +
                        '                                    <div class="panel panel-gray-bg panel_common">' + $(this).next('.label').html() + '</div>\n' +
                        '</div>';
                    $(html).draggable({
                        helper: function (e) {
                            return e.target;
                        },
                        stop: function (e, ui) {
                            var html = ui.helper.html();
                            ui.helper.remove();
                            $('#append_area').append('<div class="cell valign-center">\n' +
                                '                                        <div class="panel panel-gray-bg item_a_6_4_to_67">\n' +
                                '                                            <button  class="button_top_right_corner"><i class="icon icon-close"></i></button>\n' +
                                '                                            <span class="label" style="display: table;margin: 71px auto;">' + html + '</span>\n' +
                                '                                        </div>\n' +
                                '                                    </div>');
                        }
                    }).appendTo($('#appen-area-one'));
                    $(this).parent().parent().remove();
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
            alert("duplicate button is clicked")
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
            vm.$window.storage( 'inputData', [] );
            vm.$window.modal( "/view/kha/003/b/index.xhtml" ).then( () => {

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


