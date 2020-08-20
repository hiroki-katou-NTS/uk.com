<template>
<div class="kafs08b">
    <h3>Hello {{title | i18n}} component!</h3>
    <child
        v-bind:title='title'
        v-on:xyz="onChangeTitle" />
</div>
</template>