module nts.custombinding {
    interface ISetting {
        typeOfEnter: KnockoutObservable<number>;
        fixedColumns: KnockoutObservableArray<string>;
    }

    export class SettingButtonControl implements KnockoutBindingHandler {
        init = (element: HTMLElement, valueAccessor: () => ISetting, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let dialog = document.createElement('div'),
                dialogHtml = `<style type='text/css' rel='stylesheet'>
                .cps003.setting-dialog {
                    position: absolute;
                    width: 385px;
                    border: 1px solid #aaa;
                    border-radius: 7px;
                    background-color: #ececec;
                    z-index: 9999;
                    padding: 7px;
                    box-sizing: border-box;
                }
                .cps003.setting-dialog .form-group {
                    position: relative;
                    margin-bottom: 15px;
                }
                .cps003.setting-dialog .form-group>* {
                    vertical-align: middle;                    
                }
                .cps003.setting-dialog .ui-igcombo-wrapper {                    
                    position: absolute;
                    top: 2px;
                    left: 210px;
                }
                .cps003.setting-dialog .form-group .multicheckbox-wrapper {
                    margin-top: -30px;
                    padding-left: 175px;
                }
                .cps003.setting-dialog .form-group .multicheckbox-wrapper .ntsCheckBox {
                    display: block;
                }
                .cps003.setting-dialog .form-group.btn-group {
                    text-align: center;
                    background-color: #fff;
                    padding: 7px;
                    margin: 0 -7px -7px -7px;
                    border-radius: 0 0 7px 7px;
                }
                </style>
                <div class="form-group">
                    <div data-bind="ntsFormLabel: { text: i18n('CPS003_25') }"></div>
                    <button data-bind="ntsHelpButton: {textId: 'CPS003_26', position: 'bottom center' }">？</button>
                    <div data-bind="ntsComboBox: {
                            width: 120,
                            name: i18n('CPS003_25'),
                            value: typeEnt,
                            options: ko.observableArray([
                                { type: 1, name: 'する' },
                                { type: 0, name: 'しない' }
                            ]),
                            optionsText: 'name',
                            optionsValue: 'type',
                            visibleItemsCount: 10,
                            dropDownAttachedToBody: true,
                            columns: [
                                { prop: 'name', length: 14}
                            ]
                        }"></div>
                </div>
                <div class="form-group">
                    <div data-bind="ntsFormLabel: { text: i18n('CPS003_27') }"></div>
                    <div data-bind="ntsMultiCheckBox: {
                            options: ko.observableArray([
                                { id: 1, name: i18n('CPS003_28'), enable: false },
                                { id: 2, name: i18n('CPS003_29'), enable: false },
                                { id: 3, name: i18n('CPS003_30'), enable: true },
                                { id: 4, name: i18n('CPS003_31'), enable: true },
                                { id: 5, name: i18n('CPS003_32'), enable: true },
                                { id: 6, name: i18n('CPS003_33'), enable: true },
                                { id: 7, name: i18n('CPS003_34'), enable: true }
                            ]),
                            optionsValue: 'id',
                            optionsText: 'name',
                            value: fixCols,
                            enable: true
                        }"></div>
                </div>
                <div class="form-group btn-group">
                    <button class="x-large proceed" data-bind="text: i18n('CPS003_35'), click: pushData"></button>
                </div>`,
                dialogVm = {
                    i18n: nts.uk.resource.getText,
                    typeEnt: ko.observable(),
                    fixCols: ko.observableArray([]),
                    pushData: () => {
                        let access: ISetting = valueAccessor() || {
                            typeOfEnter: ko.observable(),
                            fixedColumns: ko.observableArray([])
                        };

                        // bind data out
                        if (ko.isObservable(access.typeOfEnter) && ko.isObservable(access.fixedColumns)) {
                            access.typeOfEnter(dialogVm.typeEnt());
                            access.fixedColumns(dialogVm.fixCols());
                        }

                        // hide dialog
                        ko.utils.triggerEvent(element, 'click');
                    }
                };

            // bind data in
            ko.computed({
                read: () => {
                    let access: ISetting = valueAccessor(),
                        typeEnt = ko.toJS(access.typeOfEnter),
                        fixCols = ko.toJS(access.fixedColumns);

                    if (!fixCols) {
                        fixCols = [1, 2];
                    }

                    if (fixCols.indexOf(1) == -1) {
                        fixCols.push(1);
                    }

                    if (fixCols.indexOf(2) == -1) {
                        fixCols.push(2);
                    }

                    dialogVm.typeEnt(typeEnt || 0);
                    dialogVm.fixCols(fixCols || [1, 2]);
                }
            });

            // set root class for dialog container
            dialog.className = 'cps003 setting-dialog';

            // apply new $vm to dialog
            ko.utils.setHtml(dialog, dialogHtml);
            ko.applyBindingsToDescendants(dialogVm, dialog);

            // show dialog
            ko.utils.registerEventHandler(element, 'click', (evt: MouseEvent) => {
                if (document.body.contains(dialog)) {
                    document.body.removeChild(dialog);
                } else {
                    let bound = element.getBoundingClientRect();

                    document.body.appendChild(dialog);
                    dialog.style.top = (bound.bottom + 5) + 'px';
                    dialog.style.left = (bound.left + bound.width - dialog.offsetWidth) + 'px';
                }
            });

            // hide dialog
            ko.utils.registerEventHandler(document, 'click', (evt: MouseEvent) => {
                if (evt.target != element && document.body.contains(dialog)) {
                    let bound = dialog.getBoundingClientRect();

                    if (bound.top > evt.pageY || bound.right < evt.pageX || bound.bottom < evt.pageY || bound.left > evt.pageX) {
                        document.body.removeChild(dialog);
                    }
                }
            });

            return { controlsDescendantBindings: true };
        }
    }
}

ko.bindingHandlers["cps003Setting"] = new nts.custombinding.SettingButtonControl();