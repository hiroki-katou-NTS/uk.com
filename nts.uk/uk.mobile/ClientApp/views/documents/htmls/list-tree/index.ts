import { obj, date } from '@app/utils';
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'documentshtmlslist-tree',
    route: {
        url: '/htmls/list-tree',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    }
})
export class DocumentsHtmlsListTreeComponent extends Vue {
    public single: boolean = true;
    public selected: any = {};
    public selecteds: any[] = [];

    public items: Array<any> = [{
        id: 1,
        text: 'Folder 1',
        state: {
            selected: false
        },
        children: [
            {
                id: 2,
                text: 'Sub Folder 1',
                state: {
                    selected: false
                },
                children: [{
                    id: 5,
                    text: 'Sub Folder 5'
                }, {
                    id: 6,
                    text: 'Sub Folder 6'
                }]
            },
            {
                id: 3,
                text: 'Sub Folder 2',
                state: {
                    selected: false
                },
                children: [{
                    id: 7,
                    text: 'Sub Folder 7'
                }, {
                    id: 8,
                    text: 'Sub Folder 8'
                }]
            }
        ]
    },
    {
        id: 4,
        text: 'Folder 2',
        state: {
            selected: true
        },
        children: []
    }];

    get flatten() {
        return obj.hierarchy(this.items, 'children');
    }
}