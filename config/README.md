
<!-- SPDX-FileCopyrightText: Copyright 2022 Alex Murphy <amsupernova@pm.me> -->
<!-- -->
<!-- SPDX-License-Identifier: CC-BY-4.0 -->

# [neovim](https://neovim.io/) configuration

_Install notes to self for VMs etc._

Create config directory:

```bash
mkdir ~/.config/nvim
```

Install [vim-plug](https://github.com/junegunn/vim-plug) via script (notice it's going into `.local` not `.config`):

```bash
curl -fLo ~/.local/share/nvim/site/autoload/plug.vim --create-dirs https://raw.githubusercontent.com/junegunn/vim-plug/master/plug.vim`.
```

Then run:

```bash
git clone https://github.com/galacticalex/config.git ~/.config/nvim
```

The first time neovim is opened it might complain because a colour scheme is not installed. This is not a problem. Proceed and run from ex-mode:

`:PlugInstall` for plugins, and then the treesitter grammar with:

`:TSInstall python`.

Exit and re-open neovim. 

