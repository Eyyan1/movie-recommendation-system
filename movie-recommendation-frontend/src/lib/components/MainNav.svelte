<script lang="ts">
	import { page } from '$app/state';

	type NavItem = {
		label: string;
		href: string;
		auth?: 'anonymous' | 'authenticated' | 'admin';
	};

	let {
		user = { name: 'Ava', role: 'USER', authenticated: false },
		accent = 'Movie discovery, ratings, and recommendations'
	}: {
		user?: { name: string; role: 'USER' | 'ADMIN'; authenticated: boolean };
		accent?: string;
	} = $props();

	const navItems: NavItem[] = [
		{ label: 'Home', href: '/' },
		{ label: 'Movies', href: '/movies' },
		{ label: 'Recommendations', href: '/recommendations', auth: 'authenticated' },
		{ label: 'My Ratings', href: '/my-ratings', auth: 'authenticated' },
		{ label: 'Admin Movies', href: '/admin/movies', auth: 'admin' }
	];

	function showItem(item: NavItem) {
		if (!item.auth) return true;
		if (item.auth === 'anonymous') return !user.authenticated;
		if (item.auth === 'authenticated') return user.authenticated;
		return user.authenticated && user.role === 'ADMIN';
	}
</script>

<nav class="sticky top-0 z-40 border-b border-white/10 bg-slate-950/85 backdrop-blur-xl">
	<div class="mx-auto flex max-w-7xl flex-col gap-4 px-4 py-4 sm:px-6 lg:px-8">
		<div class="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
			<div class="flex items-center gap-4">
				<a href="/" class="inline-flex items-center gap-3">
					<div class="flex h-11 w-11 items-center justify-center rounded-2xl bg-gradient-to-br from-amber-300 via-orange-500 to-rose-600 text-sm font-bold text-slate-950 shadow-lg shadow-orange-900/30">
						MR
					</div>
					<div>
						<p class="font-display text-xl tracking-tight text-white">Movie Recommendation</p>
						<p class="text-sm text-slate-400">{accent}</p>
					</div>
				</a>
			</div>

			<div class="flex flex-wrap items-center gap-2">
				{#each navItems as item}
					{#if showItem(item)}
						<a
							href={item.href}
							class={`rounded-full px-4 py-2 text-sm font-medium transition ${
								page.url.pathname === item.href
									? 'bg-white text-slate-950'
									: 'text-slate-300 hover:bg-white/8 hover:text-white'
							}`}
						>
							{item.label}
						</a>
					{/if}
				{/each}

				{#if user.authenticated}
					<div class="ml-2 rounded-full border border-white/10 bg-white/5 px-4 py-2 text-sm text-slate-300">
						Signed in as <span class="font-semibold text-white">{user.name}</span>
					</div>
					<a
						href="/login"
						class="rounded-full border border-white/15 px-4 py-2 text-sm font-medium text-white transition hover:bg-white/10"
					>
						Logout
					</a>
				{:else}
					<a
						href="/login"
						class="rounded-full border border-white/15 px-4 py-2 text-sm font-medium text-white transition hover:bg-white/10"
					>
						Login
					</a>
					<a
						href="/register"
						class="rounded-full bg-amber-300 px-4 py-2 text-sm font-semibold text-slate-950 transition hover:bg-amber-200"
					>
						Register
					</a>
				{/if}
			</div>
		</div>
	</div>
</nav>
