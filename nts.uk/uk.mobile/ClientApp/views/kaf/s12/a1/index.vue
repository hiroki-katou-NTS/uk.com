<template>
  <div class="kafs12a1">
    <div>
      <kafs00-a
        v-if="kaf000_A_Params != null"
        v-bind:params="kaf000_A_Params"
      />
    </div>
    <!-- A2_1 -->
    <div
      v-if="!$valid || !isValidateAll"
      class="card bg-danger top-alert uk-text-danger topError"
    >
      <button class="btn btn-link uk-text-danger">
        <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
        {{ "KAFS08_13" | i18n }}
      </button>
    </div>
    <div>
      <kafs00-b
        v-if="kaf000_B_Params != null"
        v-bind:params="kaf000_B_Params"
        @kaf000BChangeDate="kaf000BChangeDate"
        @kaf000BChangePrePost="kaf000BChangePrePost"
      />
    </div>
  </div>
</template>