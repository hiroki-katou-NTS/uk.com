import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/accordion',
        parent: '/documents'
    },
    template: require('./index.html'),
    resource: {
        'vi': {
            'AccordionDocumentComponent': 'Accordions/Step Wizard',
            'next': 'Tiếp',
            'preview': 'Lùi lại'
        },
        'jp': {
            'AccordionDocumentComponent': 'Accordions/Step Wizard',
            'next': 'Next',
            'preview': 'Preview'
        }
    },
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/vi.md')
    }
})
export class AccordionDocumentComponent extends Vue {
}