import { Vue } from '@app/provider';
import { component } from '@app/core/component';

import { NavbarComponent } from './nav';

@component({
    route: '/documents',
    template: `<div id="document_index" class="row">
        <navi-bar :class="'col-md-2'" />
        <router-view :class="'col-md-10'" />
    </div>`,
    style: `#document_index { margin-bottom: 15px; }`,
    components: {
        'navi-bar': NavbarComponent
    },
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
export class DocumentIndex extends Vue { }