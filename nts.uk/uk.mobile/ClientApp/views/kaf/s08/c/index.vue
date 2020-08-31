<template>
<div class="kafs08c">
    <h3>Hello {{title | i18n}} component!</h3>
    <nts-text-editor
        name='title'
        v-model='title' />
</div>
</template>