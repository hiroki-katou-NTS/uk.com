class ProfileHandler implements KnockoutBindingHandler {
    init(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        let params: any = valueAccessor();
        if (params.enable == undefined) {
            params.enable = true;
        }

        let container1: HTMLElement = document.createElement('div'),
            container2: HTMLElement = document.createElement('div'),
            searchBtn: HTMLElement = document.createElement('button'),
            previewBtn: HTMLElement = document.createElement('button'),
            nextBtn: HTMLElement = document.createElement('button'),
            input: HTMLElement = document.createElement('input'),
            label: HTMLElement = document.createElement('label'),
            labelName: HTMLElement = document.createElement('label');

        if (!params.enable) {
            input.setAttribute('disabled', 'disabled');
            searchBtn.setAttribute('disabled', 'disabled');
            previewBtn.setAttribute('disabled', 'disabled');
            nextBtn.setAttribute('disabled', 'disabled');
        }

        input.setAttribute('type', 'text');
        input.setAttribute('readonly', 'readonly');
        input.classList.add("nts-editor");
        input.classList.add("nts-input");
  

        previewBtn.onclick = () => {
            let options = ko.toJS(params.options), index = _.findIndex(options, m => m[params.key] == params.value()[params.key]);
            if (index > 0) {
                params.value(options[--index]);
            }
        };

        nextBtn.onclick = () => {
            let options = ko.toJS(params.options), index = _.findIndex(options, m => m[params.key] == params.value()[params.key]);
            if (index < options.length - 1) {
                params.value(options[++index]);
            }
        };

        params.value.subscribe((v) => {
            if (v) {
                label.innerText = v[params.name];
                input.setAttribute('value', v[params.code]);

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
                input.setAttribute('value', '');
            }
        });
        
        params.value.subscribe((v1) => {
            if (v1) {
                labelName.innerText = v1[params.code];
                input.setAttribute('value', v1[params.code]);
            } else {
                labelName.innerText = '';
                input.setAttribute('value', '');
            }
        });

        params.options.subscribe((v) => {
            let options = ko.toJS(params.options);
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
        labelName.classList.add('nts-label');
        labelName.classList.add('nts-name');
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

