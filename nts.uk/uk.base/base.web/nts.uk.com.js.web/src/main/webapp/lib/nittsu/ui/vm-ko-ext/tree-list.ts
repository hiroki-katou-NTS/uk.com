module nts.uk.ui.components.treelist {
    type TreeListParams = {
        options: any[] | KnockoutObservableArray<any>;
    };

    @component({
        name: 'tree-list',
        template: `<ul data-bind="foreach: $component.params.options">
            <li>
                <div class="title" data-bind="text: $data.title"></div>
                <div data-bind="component: { name: 'tree-list', params: { options: $data.childs } }"></div>
            </li>
        </ul>
        <style>
            div.tree-list ul div.tree-list {
                padding-left: 15px;
            }
            div.tree-list li:not(:last-child) {
                padding-bottom: 2px;
            }
            div.tree-list .title {
                padding-left: 3px;
                border-radius: 3px;
            }
        </style>`
    })
    export class TreeListComponent extends ko.ViewModel {
        constructor(private params: TreeListParams) {
            super();

            if (this.params === undefined) {
                this.params = {
                    options: ko.observableArray([])
                };
            }

            const { options } = this.params;

            if (options === undefined) {
                this.params.options = ko.observableArray([]);
            }
        }

        created() {
        }

        mounted() {
            const vm = this;
            const { $el, params } = vm;
            const { options } = params;

            $el.classList.add('tree-list');

            ko.computed({
                read: () => {
                    const opts = ko.unwrap(options) as any[];

                    if (!opts.length) {
                        $el.style.display = 'none';
                    } else {
                        $el.style.display = 'block';
                    }

                    $el.removeAttribute('data-bind');
                    $($el).find('[data-bind]').removeAttr('data-bind');
                },
                disposeWhenNodeIsRemoved: $el
            });
        }
    }
}