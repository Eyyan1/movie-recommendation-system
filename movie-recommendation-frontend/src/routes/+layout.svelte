<script lang="ts">
	import './layout.css';
	import favicon from '$lib/assets/favicon.svg';
	import Footer from '$lib/components/Footer.svelte';
	import MainNav from '$lib/components/MainNav.svelte';

	let { children } = $props();
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
	<title>Movie Recommendation Frontend</title>
	<meta
		name="description"
		content="A modern SvelteKit frontend foundation for a movie recommendation platform."
	/>
</svelte:head>

<div class="min-h-screen bg-slate-950 text-white">
	<MainNav />
	<div class="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8 lg:py-12">
		{@render children()}
	</div>
	<Footer />
</div>
