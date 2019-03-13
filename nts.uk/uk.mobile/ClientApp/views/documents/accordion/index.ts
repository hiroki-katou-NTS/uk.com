import { Vue } from '@app/provider';
import { routes } from '@app/core/routes';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/accordion',
        parent: '/documents'
    },
    template: require('./index.html')
})
export class AccordionDocumentComponent extends Vue {
}