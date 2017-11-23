class ProfileHandler implements KnockoutBindingHandler {
    init(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        let params: any = valueAccessor();
        if (params.enable == undefined) {
            params.enable = true;
        }

        if (params.showTxtEmployee == undefined) {
            params.showTxtEmployee = true;
        }

        let container1: HTMLElement = document.createElement('div'),
            container2: HTMLElement = document.createElement('div'),
            searchBtn: HTMLElement = document.createElement('button'),
            previewBtn: HTMLElement = document.createElement('button'),
            nextBtn: HTMLElement = document.createElement('button'),
            input: HTMLElement = document.createElement('input'),
            label: HTMLElement = document.createElement('label'),
            labelName: HTMLElement = document.createElement('label'),
            labelPerson: HTMLElement = document.createElement('label');

        if (!params.enable) {
            input.setAttribute('disabled', 'disabled');
            searchBtn.setAttribute('disabled', 'disabled');
            previewBtn.setAttribute('disabled', 'disabled');
            nextBtn.setAttribute('disabled', 'disabled');
        } else {
            input.onchange = e => {
                let options = ko.toJS(params.options),
                    option = _.find(options, m => m[params.code] == e.target['value']);
                if (option) {
                    params.value(option);
                } else {
                    if (params.error) {
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_7"));
                    }
                    e.target['value'] = params.value()[params.code];
                }
            };
        }

        if (!params.showTxtEmployee) {
            input.setAttribute("style", "display: none;");
        }

        input.id = "profile-input";
        input.setAttribute("tabindex", '0');
        input.setAttribute('type', 'text');
        input.classList.add("nts-editor");
        input.classList.add("nts-input");

        params.value.subscribe((v) => {
            if (v) {
                label.innerText = v[params.name];
                labelName.innerText = v[params.code];

                let _options: Array<any> = ko.toJS(params.options);
                labelPerson.innerText = '1' + '/' + _options.length + ' 人';

                let options = ko.toJS(params.options), index = _.findIndex(options, m => m[params.key] == v[params.key]);
                if (index == options.length - 1) {
                    nextBtn.setAttribute('disabled', 'disabled');
                } else if (params.enable) {
                    nextBtn.removeAttribute('disabled');
                }
                if (index == 0) {
                    previewBtn.setAttribute('disabled', 'disabled');
                } else if (params.enable) {
                    previewBtn.removeAttribute('disabled');
                }

            } else {
                label.innerText = '';
                labelName.innerText = '';
                labelPerson.innerText = '';
            }
            input['value'] = '';
        });

        previewBtn.onclick = () => {
            let options = ko.toJS(params.options), index = _.findIndex(options, m => m[params.key] == params.value()[params.key]);
            if (index > 0) {
                input['value'] = '';
                params.value(options[--index]);
                nts.uk.ui.errors.clearAll();
            }
        };

        nextBtn.onclick = () => {
            let options = ko.toJS(params.options), index = _.findIndex(options, m => m[params.key] == params.value()[params.key]);
            if (index < options.length - 1) {
                input['value'] = '';
                params.value(options[++index]);
                nts.uk.ui.errors.clearAll();
            }
        };

        params.options.subscribe((v) => {
            let options = ko.toJS(params.options);
            labelPerson.innerText =  '1' + '/' + (options.length ? options.length: '1' ) + ' 人'

            if (!options.length) {
                params.value(undefined);
                nextBtn.setAttribute('disabled', 'disabled');
                previewBtn.setAttribute('disabled', 'disabled');
            } else {
                let index = _.findIndex(options, m => m[params.key] == (params.value() || {})[params.key]);
                if (index >= 0) {
                    if (index == options.length - 1) {
                        nextBtn.setAttribute('disabled', 'disabled');
                    } else if (params.enable) {
                        nextBtn.removeAttribute('disabled');
                    }
                    if (index == 0) {
                        previewBtn.setAttribute('disabled', 'disabled');
                    } else if (params.enable) {
                        previewBtn.removeAttribute('disabled');
                    }
                } else {
                    params.value(options[0]);
                }
            }
        });

        params.value.valueHasMutated();
        params.options.valueHasMutated();

        label.classList.add('nts-label');
        label.classList.add('nts-fullname');
        labelName.classList.add('nts-label');
        labelName.classList.add('nts-name');
        labelPerson.classList.add('nts-label');
        labelPerson.classList.add('nts-person');

        if (params.searchEvent) {
            searchBtn.setAttribute('data-bind', 'click: ' + params.searchEvent);
        }

        searchBtn.innerText = '対象者';
        searchBtn.classList.add('btn-search');
        previewBtn.innerText = '◀';
        previewBtn.classList.add('btn-preview');
        nextBtn.innerText = '▶';
        nextBtn.classList.add('btn-next');

        container1.appendChild(searchBtn);
        container1.appendChild(previewBtn);
        container1.appendChild(nextBtn);
        container1.classList.add('left-container');


        container2.appendChild(labelName);

        container2.appendChild(label);
        container2.appendChild(labelPerson);
        container2.appendChild(input);
        container2.classList.add('right-container');

        element.classList.add('ntsControl');
        element.classList.add('ntsProfile');
        element.appendChild(container1);
        element.appendChild(container2);

        ko.applyBindingsToNode(container1, viewModel);
        ko.applyBindingsToNode(container2, viewModel);
    }

    update(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext): void {
    }
}

ko.bindingHandlers["ntsProfile"] = new ProfileHandler();

