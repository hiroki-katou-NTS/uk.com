import { Vue } from '@app/provider';

import { obj } from '@app/utils';
import { menu } from '@app/utils/menu';
import { routes } from '@app/core/routes';
import { SideMenu } from '@app/services';
import { component } from '@app/core/component';

@component({
    route: '/documents',
    template: `<router-view />`,
    style: `#document_index { margin-bottom: 15px; }`,
    resource: {
        vi: {
            documents: 'Tài liệu',
            MarkdownComponent: 'Markdown'
        },
        jp: {
            documents: 'Documents',
            MarkdownComponent: 'Markdown'
        }
    }
})
export class DocumentIndex extends Vue {
    items: Array<any> = [];

    mounted() {
        this.items = SideMenu.items;

        SideMenu.items = menu.sample;
    }

    destroyed() {
        SideMenu.items = this.items;
    }
}