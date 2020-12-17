<template>
<div class="kafs05step2">
    <div class="card card-label">
        <!-- A2_C1_1 -->
        <button
      type="button"
      class="btn btn-block btn-primary btn-lg"
      v-if="true"
    >
      {{nameInsert}}
    </button>
    <!-- A2_C1_2 -->
    <button
      type="button"
      class="btn btn-block btn-secondary"
      v-if="true"
      v-on:click="backStep1"
    >
      {{ "KAFS05_12" | i18n }}
    </button>
    </div>
</div>
</template>